package com.example.engagecommerce.presentation.ui.transaction.checkout

/*

@AndroidEntryPoint
@ScreenName(name = "Checkout")
class CheckoutFragment : RootFragment(), View.OnClickListener {

    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var viewModel: CheckoutViewModel
    private lateinit var viewModelFactory: CheckoutViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_checkout,
            container,
            false
        )
        setAnimation()

        val cartValueBundle = CheckoutFragmentArgs
            .fromBundle(requireArguments())
            .cartValue

        viewModelFactory = CheckoutViewModelFactory(cartValueBundle)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CheckoutViewModel::class.java)

        viewModel.user?.observe(viewLifecycleOwner, {
            bindUserData(it)
        })

        binding.textTotalAmountToPay.text = cartValueBundle
        binding.buttonOrder.setOnClickListener(this)
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.buttonOrder -> {
                viewModel.placeOrder()
                restartMainActivity()
            }
        }
    }

    private fun bindUserData(userEntity: UserEntity) {
        val locale = Utils.locale
        binding.textFirstNameValue.text = userEntity.firstName?.capitalize(locale)
        binding.textLastNameValue.text = userEntity.lastName?.capitalize(locale)
    }
}*/
