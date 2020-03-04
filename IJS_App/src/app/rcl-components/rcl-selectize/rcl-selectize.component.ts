import {
	Input,
	OnInit,
	OnChanges,
	SimpleChanges,
	DoCheck,
	forwardRef,
	Component,
	ViewChild,
	Output,
	EventEmitter,
	Optional,
	Inject
} from "@angular/core";
declare var jQuery: any;
import * as lodash from 'lodash';

import {NG_ASYNC_VALIDATORS, NG_VALIDATORS, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl, NgModel} from "@angular/forms";

import {ElementBase} from '../rcl-base/element-base';

declare const $: any;

export const SELECTIZE_VALUE_ACCESSOR: any = {
	provide: NG_VALUE_ACCESSOR,
	useExisting: forwardRef(() => RclSelectizeComponent),
	multi: true
};

let identifier = 0;

@Component({
	selector: 'app-rcl-selectize',
	templateUrl: './rcl-selectize.component.html',
	styleUrls: ['./rcl-selectize.component.scss'],
	providers: [SELECTIZE_VALUE_ACCESSOR]
})
export class RclSelectizeComponent extends ElementBase<string[]> implements OnInit, OnChanges, DoCheck, ControlValueAccessor {
	@ViewChild(NgModel) model: NgModel;
	@Input('config') config: any;
	@Input('options') options: any[];
	@Input('optionGroups') optionGroups: any[];
	@Input('placeholder') placeholder: string;
	@Input('hasOptionsPlaceholder') hasOptionsPlaceholder: string;
	@Input('noOptionsPlaceholder') noOptionsPlaceholder: string;
	@Input('enabled') enabled: boolean = true;
	@Input('ngModel') _value: string[];
	@Input() formControl: FormControl;
	@Input() errorClass: string;
	@Input() klass: string;
	@Input() label: string;
	@Input() compid: string;
	@Input() disabled: boolean;

	@Output() onBlur: EventEmitter<void> = new EventEmitter<void>(false);
	@Output() onChangeValue = new EventEmitter();
	@Output() onLocChange = new EventEmitter();

	@ViewChild('selectizeInput') selectizeInput: any;

	public identifier = `rcl-select2-${identifier++}`;
	public touchedFlag: boolean = false;

	public selectize: any;
	public _oldOptions: any[];

	public _oldOptionGroups: any[];
	// Control value accessors.
	public onTouchedCallback: () => void = () => { };
	public onChangeCallback: (_: any) => void = () => { };

	constructor(
		@Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
		@Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>) {
		super(validators, asyncValidators);
	}

	ngOnInit(): void {
		this.selectize = $(this.selectizeInput.nativeElement).selectize(this.config)[0].selectize;
		this.reset();
		if (this.selectize) {
			if (this.disabled == true) {
				this.selectize.disable();
			}
		}
		
	}
	//to reset the dropdown-list
	resetSelectSize() {
		this.selectize.clear();
	}




	reset() {
		this.selectize.on('change', this.onSelectizeValueChange.bind(this));
		this.selectize.on('option_add', this.onSelectizeOptionAdd.bind(this));
		this.selectize.on('blur', this.onBlurEvent.bind(this));

		this.onSelectizeOptionsChange();
		this.onSelectizeOptionGroupChange();
		if (this.placeholder && this.placeholder.length > 0) {
			this.updatePlaceholder();
		}
		this._oldOptions = lodash.cloneDeep(this.options);
		this._oldOptionGroups = lodash.cloneDeep(this.optionGroups);

		this.onEnabledStatusChange();
	}

	/**
	 * Change detection for primitive types.
	 */
	ngOnChanges(changes: SimpleChanges): void {
		if (this.selectize) {
			if (changes.hasOwnProperty('placeholder') || changes.hasOwnProperty('hasOptionsPlaceholder') || changes.hasOwnProperty('noOptionsPlaceholder')) {
				this.updatePlaceholder();
			}
			if (changes.hasOwnProperty('enabled')) {
				this.onEnabledStatusChange();
			}
			if (this.config.maxItems == 1) {
				this.selectize.setValue(this._value, false);
			} else if (this.config.maxItems > 1) {
				this.selectize.setValue(this._value, true);
			}
			if (changes.hasOwnProperty('disabled')) {
				if (changes['disabled'].currentValue == true) {
					this.selectize.disable();
				} else {
					this.selectize.enable();
				}
			}
		}
	}

	/**
	 * Implementing deep check for option comparison
	 *
	 * FIXME -> Implement deep check to only compare against label and value fields.
	 */
	ngDoCheck(): void {
		if (!lodash.isEqual(this._oldOptions, this.options)) {
			this.onSelectizeOptionsChange();
			this._oldOptions = lodash.cloneDeep(this.options);
		}
		if (!lodash.isEqual(this._oldOptionGroups, this.optionGroups)) {
			this.onSelectizeOptionGroupChange();
			this._oldOptionGroups = lodash.cloneDeep(this.optionGroups);
		}
		this.evalHasError();
	}

	onBlurEvent() {
		if (this.formControl) {
			this.formControl.markAsTouched();
		}
		this.onBlur.emit();
		this.evalHasError();
		this.onTouchedCallback();
		this.touchedFlag = true;
	}

	/**
	 * Refresh selected values when options change.
	 */
	onSelectizeOptionAdd(optionValue: string): void {
		if (this.value) {
			const items = typeof this.value === 'string' ? [this.value] : this.value;
			if (lodash.find(items, (value: any) => {
				return value === optionValue
			})) {
				this.selectize.addItem(optionValue, true);
			}
		}
	}

	evalHasError() {
		if (this.formControl) {
			if (this.formControl.touched && this.formControl.invalid) {
				$(this.selectize.$control).parent().addClass(this.errorClass || 'has-error');
			} else {
				$(this.selectize.$control).parent().removeClass(this.errorClass || 'has-error');
			}
		} else {
			this.invalid.subscribe((res) => {
				if (res) {
					$(this.selectize.$control).addClass(this.errorClass || 'has-error');
				} else {
					$(this.selectize.$control).removeClass(this.errorClass || 'has-error');
				}
			});
		}
	}

	/**
	 * Update the current placeholder based on the given input parameter.
	 */
	updatePlaceholder(): void {
		this.selectize.settings.placeholder = this.getPlaceholder();
		this.selectize.updatePlaceholder();
		this.selectize.showInput(); // Without this, when options are cleared placeholder only appears after focus.
	}

	/**
	 * Called when a change is detected in the 'enabled' input field.
	 * Sets the selectize state based on the new value.
	 */
	onEnabledStatusChange(): void {
		this.enabled ? this.selectize.enable() : this.selectize.disable();
	}

	/**
	 * Triggered when a change is detected with the given set of options.
	 */
	onSelectizeOptionsChange(): void {
		const optionsRemoved = lodash.differenceWith(this._oldOptions, this.options, (oldValue: any, newValue: any) => {
			return oldValue[this.selectize.settings.valueField] === newValue[this.selectize.settings.valueField]
				&& oldValue[this.selectize.settings.labelField] === newValue[this.selectize.settings.labelField];
		});

		const newOptionsAdded = lodash.differenceWith(this.options, this._oldOptions, (oldValue: any, newValue: any) => {
			return oldValue[this.selectize.settings.valueField] === newValue[this.selectize.settings.valueField]
				&& oldValue[this.selectize.settings.labelField] === newValue[this.selectize.settings.labelField];
		});

		if (optionsRemoved && optionsRemoved.length > 0) {
			optionsRemoved.forEach((option: any) => {
				this.selectize.removeOption(option[this.selectize.settings.valueField]);
			});
		}

		if (newOptionsAdded && newOptionsAdded.length > 0) {
			newOptionsAdded.forEach((option: any) => {
				this.selectize.addOption(lodash.cloneDeep(option));
			});
		}
		this.updatePlaceholder();
	}

	/**
	 * Triggered when a change is detected with the given set of option groups.
	 */
	onSelectizeOptionGroupChange(): void {
		if (this.optionGroups != null && this.optionGroups.length > 0) {
			this.optionGroups.forEach((optionGroup) => {
				this.selectize.addOptionGroup(optionGroup.id, optionGroup);
			});
		}
	}

	/**
	 * Dispatches change event when a value change is detected.
	 * @param $event
	 */
	value1:any;
	onSelectizeValueChange($event: any): void {		
		this.value1 = this.selectize.getValue();
		this.onChangeValue.emit($event);
		this.value = $event;

	
		
	}

	/**
	 * Returns the applicable placeholder.
	 */
	getPlaceholder(): string {
		let newPlaceholder: string;
		if (this.options != null && this.options.length > 0 && this.hasOptionsPlaceholder != null && this.hasOptionsPlaceholder.length > 0) {
			newPlaceholder = this.hasOptionsPlaceholder;
		} else if ((this.options == null || this.options.length == 0) && (this.noOptionsPlaceholder != null && this.noOptionsPlaceholder.length > 0)) { // no options
			newPlaceholder = this.noOptionsPlaceholder
		} else {
			newPlaceholder = this.placeholder;
		}
		return newPlaceholder;
	}

	/**
	 * Implementation from ControlValueAccessor, callback for (ngModelChange)
	 * @param fn
	 */
	registerOnChange(fn: any): void {
		this.onChangeCallback = fn;
	}

	/**
	 * Implementation from ControlValueAccessor
	 * @param fn
	 */
	registerOnTouched(fn: any): void {
		this.onTouchedCallback = fn;
	}

	get value(): string[] {
		return this._value;
	}

	set value(value: string[]) {
		if (this._value !== value) {
			setTimeout(() => { // Fix for change after check issue in development mode.
				this._value = lodash.cloneDeep(value);
				this.onChangeCallback(this._value);
			});
		}
	}


}
